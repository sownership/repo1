package server.controller;

import java.nio.ByteBuffer;
import java.util.List;

import server.convert.AbsConvert;
import server.util.DependancyInjectionUtil;

public abstract class AbsConvertController implements IController {

	private List<AbsConvert> converters;

	protected ByteBuffer convert(String cmd, ByteBuffer dataToConvert) {

		if (converters == null) {
			converters = DependancyInjectionUtil.getList("commandConverterMapper.properties", cmd, AbsConvert.class);
		}

		ByteBuffer result = dataToConvert;
		for (AbsConvert converter : converters) {
			result = converter.convert(result);
		}

		return result;
	}

	protected ByteBuffer convertRemain(String cmd, ByteBuffer dataToConvert) {

		if (converters == null) {
			converters = DependancyInjectionUtil.getList("commandConverterMapper.properties", cmd, AbsConvert.class);
		}

		ByteBuffer result = dataToConvert;
		for (AbsConvert converter : converters) {
			result = converter.convertRemain(result);
		}

		return result;
	}
}
