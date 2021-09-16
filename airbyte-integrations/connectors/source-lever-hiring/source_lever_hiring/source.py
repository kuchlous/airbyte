#
# MIT License
#
# Copyright (c) 2020 Airbyte
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#


from typing import Any, List, Mapping, Tuple

import pendulum
import requests
from airbyte_cdk.models import ConnectorSpecification, DestinationSyncMode
from airbyte_cdk.sources import AbstractSource
from airbyte_cdk.sources.streams import Stream
from airbyte_cdk.sources.streams.http.auth import TokenAuthenticator
from pydantic import Field
from pydantic.main import BaseModel

from .streams import Applications, Interviews, Notes, Offers, Opportunities, Referrals, Users


class ConnectorConfig(BaseModel):
    class Config:
        title = "Lever Hiring Spec"

    client_id: str = Field(
        description="The client application id as provided when registering the application with Lever.",
    )
    client_secret: str = Field(
        description="The application secret as provided when registering the application with Lever.",
        airbyte_secret=True,
    )
    refresh_token: str = Field(
        description="The refresh token your application will need to submit to get a new access token after it's expired.",
    )
    environment: str = Field(description="Sandbox or Production environment.", enum=["Sandbox", "Production"])
    start_date: str = Field(
        description="UTC date and time in the format 2019-02-25T00:00:00Z. Any data before this date will not be replicated.",
        pattern="^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z$",
        examples=["2021-04-25T00:00:00Z"],
    )


class SourceLeverHiring(AbstractSource):
    URL_MAP_ACCORDING_ENVIRONMENT = {
        "Sandbox": {"login": "https://sandbox-lever.auth0.com/", "api": "https://api.sandbox.lever.co/"},
        "Production": {"login": "https://auth.lever.co/", "api": "https://api.lever.co/"},
    }

    def _get_access_token(self, config: Mapping[str, Any]) -> str:
        body = {
            "client_id": config["client_id"],
            "client_secret": config["client_secret"],
            "refresh_token": config["refresh_token"],
            "grant_type": "refresh_token",
        }
        response = requests.post(f"{self.URL_MAP_ACCORDING_ENVIRONMENT[config['environment']]['login']}oauth/token", json=body)
        response.raise_for_status()
        return response.json()["access_token"]

    def check_connection(self, logger, config: Mapping[str, Any]) -> Tuple[bool, any]:
        _ = self._get_access_token(config)
        return True, None

    def streams(self, config: Mapping[str, Any]) -> List[Stream]:
        access_token = self._get_access_token(config)
        authenticator = TokenAuthenticator(access_token)
        full_refresh_params = {"authenticator": authenticator, "base_url": self.URL_MAP_ACCORDING_ENVIRONMENT[config["environment"]]["api"]}
        stream_params_with_start_date = {**full_refresh_params, "start_dt": int(pendulum.parse(config["start_date"]).timestamp()) * 1000}
        return [
            Applications(**stream_params_with_start_date),
            Interviews(**stream_params_with_start_date),
            Notes(**stream_params_with_start_date),
            Offers(**stream_params_with_start_date),
            Opportunities(**stream_params_with_start_date),
            Referrals(**stream_params_with_start_date),
            Users(**full_refresh_params),
        ]

    def spec(self, *args, **kwargs) -> ConnectorSpecification:
        """
        Returns the spec for this integration. The spec is a JSON-Schema object describing the required configurations (e.g: username and password)
        required to run this integration.
        """
        return ConnectorSpecification(
            documentationUrl="https://docs.airbyte.io/integrations/sources/lever-hiring",
            changelogUrl="https://docs.airbyte.io/integrations/sources/lever-hiring#changelog",
            supportsIncremental=True,
            supported_destination_sync_modes=[DestinationSyncMode.append],
            connectionSpecification=ConnectorConfig.schema(),
        )
