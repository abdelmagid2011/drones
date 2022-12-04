package com.example.drones.mapping.serializer;

import java.io.IOException;
import java.text.NumberFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PercentageSerializer extends JsonSerializer<Float>{

	@Override
	public void serialize(Float value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeString(NumberFormat.getPercentInstance().format(value));
	}

	

}
