/*
 * ExceptionPreconditionFailed.java ->
 * Copyright (C) 2012-05-08 Gábor Bernát
 * Created at: [Budapest University of Technology and Economics - Deparment of Automation and Applied Informatics]
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package net.primeranks.fs_server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Thrown to return a 412 Precondition Failed response with a list of error messages in the body.
 */
public class ExceptionPreconditionFailed extends WebApplicationException {
    private static final long serialVersionUID = 1L;
    private List<String> errors;

    public ExceptionPreconditionFailed(MediaType type, String... errors) {
        this(type, Arrays.asList(errors));
    }

    public ExceptionPreconditionFailed(MediaType type, List<String> errors) {
        super(Response.status(Response.Status.PRECONDITION_FAILED)
                .type(type)
                .entity(new GenericEntity<List<String>>(errors) {
                }).build());
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

