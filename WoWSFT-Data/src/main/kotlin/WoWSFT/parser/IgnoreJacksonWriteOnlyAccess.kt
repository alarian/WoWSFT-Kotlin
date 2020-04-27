package WoWSFT.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.introspect.Annotated
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector

class IgnoreJacksonWriteOnlyAccess : JacksonAnnotationIntrospector()
{
    override fun findPropertyAccess(m: Annotated?): JsonProperty.Access?
    {
        val access = super.findPropertyAccess(m)
        return if (access != null && access == JsonProperty.Access.WRITE_ONLY) JsonProperty.Access.AUTO else access
    }
}