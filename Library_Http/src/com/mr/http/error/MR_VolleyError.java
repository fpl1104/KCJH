/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mr.http.error;

import com.mr.http.MR_NetworkResponse;

/**
 * Exception style class encapsulating Volley errors
 */
@SuppressWarnings("serial")
public class MR_VolleyError extends Exception {
    public final MR_NetworkResponse networkResponse;

    public MR_VolleyError() {
        networkResponse = null;
    }

    public MR_VolleyError(MR_NetworkResponse response) {
        networkResponse = response;
    }

    public MR_VolleyError(String exceptionMessage) {
       super(exceptionMessage);
       networkResponse = null;
    }

    public MR_VolleyError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public MR_VolleyError(Throwable cause) {
        super(cause);
        networkResponse = null;
    }
}
