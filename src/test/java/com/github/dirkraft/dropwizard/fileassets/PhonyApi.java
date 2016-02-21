/**
 * Copyright 2010-2013 Coda Hale and Yammer, Inc.
 * Copyright 2015-2016 Jason Dunkelberger (a.k.a. dirkraft)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.dirkraft.dropwizard.fileassets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
public class PhonyApi {

    @GET
    public String hello() {
        return "Hi, you've reached all of the API!";
    }
}
