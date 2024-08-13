package com.anmolsahi.data.utils.serializers

import com.anmolsahi.data.model.remote.ListingsResponse
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

object RepliesDeserializer : JsonTransformingSerializer<ListingsResponse>(
    ListingsResponse.serializer(),
) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonObject) element else JsonObject(emptyMap())
    }
}
