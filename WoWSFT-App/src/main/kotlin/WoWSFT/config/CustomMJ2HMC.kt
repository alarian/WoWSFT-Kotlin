package WoWSFT.config

import WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.springframework.beans.factory.InitializingBean
import org.springframework.http.HttpOutputMessage
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import java.io.IOException
import java.lang.reflect.Type

class CustomMJ2HMC : MappingJackson2HttpMessageConverter(), InitializingBean {
    private lateinit var mapper: ObjectMapper

    override fun afterPropertiesSet() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    override fun setObjectMapper(objectMapper: ObjectMapper) {
        super.setObjectMapper(objectMapper)
        this.mapper = objectMapper
    }

    override fun getObjectMapper(): ObjectMapper = mapper

    @Throws(IOException::class)
    override fun writeInternal(obj: Any, type: Type?, outputMessage: HttpOutputMessage) {
        outputMessage.headers["Content-Type"] = "application/json"

        val returnMessage = convert(obj)
        val jsonEncoding = JsonEncoding.UTF8
        val jsonGenerator: JsonGenerator = mapper.factory.createGenerator(outputMessage.body, jsonEncoding)
        val objectWriter: ObjectWriter = mapper.writer()

        objectWriter.writeValue(jsonGenerator, returnMessage)
    }

    private fun convert(o: Any): Any {
        return if (o is CustomMessage) o
        else if (o is LinkedHashMap<*, *> && o.containsKey("message") || o is Throwable) CustomMessage("500", GENERAL_INTERNAL_ERROR)
        else LinkedHashMap<String, Any>().also {
            it["status"] = "200"
            it["result"] = o
        }
    }
}