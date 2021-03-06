/*
 * Copyright 2000-2019 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package arrow

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object ArrowVersionsManager {
    val arrowVersionConfigs: List<ArrowVersionConfig>
    private val arrowVersions: List<String>
    private val latestVersion: String
    private val latestStableVersion: String

    init {
        val configFile = ArrowVersionsManager::class.java.getResourceAsStream("/arrow-versions-config.json")
        arrowVersionConfigs = jacksonObjectMapper().readValue(configFile)
        arrowVersions = arrowVersionConfigs.map { it.version }
        latestVersion = arrowVersionConfigs.find { it.latest }?.version ?: throw Exception("Latest Arrow version not specified");
        latestStableVersion = arrowVersionConfigs.find { it.latestStable }?.version ?: throw Exception("Latest stable Arrow version not specified");
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ArrowVersionConfig(
    val version: String,
    val supportedKotlinVersions: List<String>,
    val latest: Boolean = false,
    val latestStable: Boolean = false)