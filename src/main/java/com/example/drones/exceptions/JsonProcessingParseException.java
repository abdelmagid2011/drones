package com.example.drones.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonProcessingParseException extends JsonProcessingException {

	public JsonProcessingParseException(String msg) {
		super(msg);
	}

}
