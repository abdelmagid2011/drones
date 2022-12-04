package com.example.drones.mapping.deserializer;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import com.example.drones.exceptions.JsonProcessingParseException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class PercentageDeserializer extends JsonDeserializer<Float>{

	@Override
	public Float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String fieldData = p.getText();
		try {
			return NumberFormat.getPercentInstance().parse(fieldData).floatValue();
		} catch (ParseException e) {
			throw new JsonProcessingParseException("Error while deserialize field " + p.currentName() +" with invalid value " + p.getText());
		}
	}

}
