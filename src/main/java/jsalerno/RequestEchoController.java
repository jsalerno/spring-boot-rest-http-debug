package jsalerno;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RequestEchoController {
	@RequestMapping("whoiscalling")
	public String whoIsCalling(HttpServletRequest r) throws JsonProcessingException {
		Map<String, String> map = new TreeMap<String, String>();
		/* headers */
		final Enumeration<String> headerNames = r.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String hname = headerNames.nextElement();
			final String hvalue = r.getHeader(hname);
			map.put("H:" + hname, hvalue);
		}

		final String localAddr = r.getLocalAddr();
		final String remoteAddr = r.getRemoteAddr();
		final String remoteHost = r.getRemoteHost();
		final String remoteUser = r.getRemoteUser();
		final int remotePort = r.getRemotePort();
		map.put("localAddr", localAddr);
		map.put("remoteAddr", remoteAddr);
		map.put("remoteHost", remoteHost);
		map.put("remoteUser", remoteUser);
		map.put("remotePort", Integer.toString(remotePort));

		final String requestURI = r.getRequestURI();
		map.put("requestURI", requestURI);

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		final String callerMapped = mapper.writeValueAsString(map);

		return (callerMapped);
	}
}