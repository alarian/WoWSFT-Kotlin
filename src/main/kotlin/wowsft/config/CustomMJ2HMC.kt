package wowsft.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.InitializingBean
import org.springframework.http.HttpOutputMessage
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.io.IOException
import java.lang.reflect.Type
import java.util.LinkedHashMap

class CustomMJ2HMC(customObjectMapper: CustomObjectMapper): MappingJackson2HttpMessageConverter(customObjectMapper), InitializingBean
{
    private var mapper: ObjectMapper = customObjectMapper

    override fun afterPropertiesSet() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    override fun setObjectMapper(objectMapper: ObjectMapper) {
        mapper = objectMapper
    }

    override fun getObjectMapper(): ObjectMapper {
        return objectMapper
    }

    @Throws(IOException::class)
    override fun writeInternal(`object`: Any, type: Type?, outputMessage: HttpOutputMessage) {
        outputMessage.headers.set("Content-Type", "application/json")

        val returnMessage = convert(`object`)
        val jsonEncoding = JsonEncoding.UTF8
        val jsonGenerator = objectMapper.factory.createGenerator(outputMessage.body, jsonEncoding)
        val objectWriter = objectMapper.writer()
        objectWriter.writeValue(jsonGenerator, returnMessage)
    }

    private fun convert(o: Any): Any {
        val lhm = LinkedHashMap<String, Any>()

        if (o is CustomMessage) {
            lhm["status"] = o.status
            lhm["message"] = o.message
        } else if (o is LinkedHashMap<*, *> && o.containsKey("status")) {
            lhm["status"] = o["status"].toString()
            lhm["message"] = o["message"].toString()
        } else {
            lhm["status"] = "200"
            lhm["result"] = o
        }

        return lhm
    }
}