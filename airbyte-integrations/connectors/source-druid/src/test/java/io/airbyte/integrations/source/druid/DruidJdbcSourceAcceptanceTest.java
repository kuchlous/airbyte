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
import io.airbyte.integrations.source.jdbc.AbstractJdbcSource;
import io.airbyte.integrations.source.jdbc.test.JdbcSourceAcceptanceTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DruidJdbcSourceAcceptanceTest extends JdbcSourceAcceptanceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DruidJdbcSourceAcceptanceTest.class);

  // TODO declare a test container for DB. EX: org.testcontainers.containers.OracleContainer

  @BeforeAll
  static void init() {
    // Oracle returns uppercase values
    // TODO init test container. Ex: "new OracleContainer("epiclabs/docker-oracle-xe-11g")"
    // TODO start container. Ex: "container.start();"
  }

  @BeforeEach
  public void setup() throws Exception {
    // TODO init config. Ex: "config = Jsons.jsonNode(ImmutableMap.builder().put("host",
    // host).put("port", port)....build());
    super.setup();
  }

  @AfterEach
  public void tearDown() {
    // TODO clean used resources
  }

  @Override
  public AbstractJdbcSource getSource() {
    return new DruidSource();
  }

  @Override
  public boolean supportsSchemas() {
    // TODO check if your db supports it and update method accordingly
    return false;
  }

  @Override
  public JsonNode getConfig() {
    return config;
  }

  @Override
  public String getDriverClass() {
    return DruidSource.DRIVER_CLASS;
  }

  @AfterAll
  static void cleanUp() {
    // TODO close the container. Ex: "container.close();"
  }

}
