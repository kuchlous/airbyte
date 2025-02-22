/*
 * MIT License
 *
 * Copyright (c) 2020 Airbyte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.airbyte.integrations.source.druid;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.db.Database;
import org.junit.jupiter.api.Test;

public class DruidSourceTests {

  private JsonNode config;
  private Database database;

  @Test
  public void testSettingTimezones() throws Exception {
    // TODO init your container. Ex: "new
    // org.testcontainers.containers.MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2019-latest").acceptLicense();"
    // TODO start the container. Ex: "container.start();"
    // TODO prepare DB config. Ex: "config = getConfig(container, dbName,
    // "serverTimezone=Europe/London");"
    // TODO create DB, grant all privileges, etc.
    // TODO check connection status. Ex: "AirbyteConnectionStatus check = new
    // ScaffoldJavaJdbcGenericSource().check(config);"
    // TODO assert connection status. Ex: "assertEquals(AirbyteConnectionStatus.Status.SUCCEEDED,
    // check.getStatus());"
    // TODO cleanup used resources and close used container. Ex: "container.close();"
  }

}
