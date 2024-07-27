package com.anmolsahi.data.serializer

import com.anmolsahi.data.model.remote.ListingsResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

object RepliesDeserializer : JsonTransformingSerializer<List<ListingsResponse>>(
    ListSerializer(ListingsResponse.serializer()),
) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonArray) element else JsonArray(emptyList())
    }
}
