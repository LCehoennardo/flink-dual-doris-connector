// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.doris.flink.cfg;

import org.apache.doris.flink.util.IOUtils;

import java.util.Properties;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Options for the Doris connector.
 */
public class DorisOptions extends DorisConnectionOptions {

    private static final long serialVersionUID = 1L;

    private String tableIdentifier;


    public DorisOptions(String master_fenodes, String slave_fenodes, String username, String password, String tableIdentifier) {
        super(master_fenodes, slave_fenodes, username, password);
        this.tableIdentifier = tableIdentifier;
    }

    public String getTableIdentifier() {
        return tableIdentifier;
    }

    public String save() throws IllegalArgumentException {
        Properties copy = new Properties();
        return IOUtils.propsToString(copy);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder of {@link DorisOptions}.
     */
    public static class Builder {
        private String master_fenodes;
        private String slave_fenodes;
        private String username;
        private String password;
        private String tableIdentifier;

        /**
         * required, tableIdentifier
         */
        public Builder setTableIdentifier(String tableIdentifier) {
            this.tableIdentifier = tableIdentifier;
            return this;
        }

        /**
         * optional, user name.
         */
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        /**
         * optional, password.
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * required, JDBC DB url.
         */
        public Builder setMasterFenodes(String master_fenodes) {
            this.master_fenodes = master_fenodes;
            return this;
        }

        public Builder setSlaveFenodes(String slave_fenodes) {
            this.slave_fenodes = slave_fenodes;
            return this;
        }


        public DorisOptions build() {
            if (master_fenodes == null && slave_fenodes == null) {
                throw new NullPointerException(String.valueOf("No fenodes supplied, please specify at least one Doris cluster."));
            }
            checkNotNull(tableIdentifier, "No tableIdentifier supplied.");
            return new DorisOptions(master_fenodes, slave_fenodes, username, password, tableIdentifier);
        }
    }


}
