package com.demoboletto.config;

import com.demoboletto.dto.global.ExceptionDto;
import com.demoboletto.dto.global.ResponseDto;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;

@Component
public class GenericModelConverter implements ModelConverter {

    @Override
    public Schema<?> resolve(AnnotatedType annotatedType, ModelConverterContext modelConverterContext, Iterator<ModelConverter> iterator) {
        Type type = annotatedType.getType();

        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();

            if (rawType == ResponseDto.class) {
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                AnnotatedType childType = new AnnotatedType()
                        .type(actualTypeArgument)
                        .resolveAsRef(true);

                Schema<?> dataSchema = modelConverterContext.resolve(childType);

                Schema<?> responseSchema = new Schema<>(); // ResponseDto의 스키마 생성

                responseSchema.addProperty("success", new Schema<Boolean>().example(true));
                responseSchema.addProperty("data", dataSchema);
                responseSchema.addProperty("error", modelConverterContext.resolve(new AnnotatedType().type(ExceptionDto.class)));

                return responseSchema;
            }
        }

        if (iterator.hasNext()) {
            return iterator.next().resolve(annotatedType, modelConverterContext, iterator);
        }

        return null;
    }
}