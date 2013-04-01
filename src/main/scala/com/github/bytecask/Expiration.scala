/*
* Copyright 2011 P.Budzik
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* User: przemek
* Date: 4/1/13
* Time: 4:30 PM
*/

package com.github.bytecask

/**
 * Expiration is meant to provide auto-expiring entries that have exceeded the predefined TTL
 */

trait Expiration {

  val bytecask: Bytecask

  val ttl: Int //Seconds !

  bytecask.tasks.append {
    options =>
      for (key <- bytecask.keys())
        for (meta <- bytecask.getMetadata(key))
          if (Utils.timestamp - meta.timestamp >= ttl)
            bytecask.delete(key)
  }

}