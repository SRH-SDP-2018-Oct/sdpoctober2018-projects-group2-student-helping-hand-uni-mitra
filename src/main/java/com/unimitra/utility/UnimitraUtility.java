package com.unimitra.utility;

import org.springframework.util.ObjectUtils;

import com.unimitra.exception.UnimitraException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnimitraUtility {

	public static void nullCheckForEntity(Object entity, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(entity)) {
			throw new UnimitraException(errorCode);
		}
	}

}
