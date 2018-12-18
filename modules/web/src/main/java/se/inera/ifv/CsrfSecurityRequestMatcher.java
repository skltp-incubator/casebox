/*
 *This class provides the security layer with a way of determine what servlets
 *requests that requires to implement "csrf-Synchronizer Token Pattern".
 *
 *This is set by the
 *-applicationContext-security.xml
 *-<csrf request-matcher-ref="csrfSecurityRequestMatcher"/>
 *
 *See https://en.wikipedia.org/wiki/Cross-site_request_forgery for mor information on how
 *to avoid Cross-Site Request Forgery.
 *
 *Note Rather than specifying what services (url's) that do requires csrf check we specify those who don't,
 *thus preventing future changes to accidentally add functionality that brakes the "Synchronized Token Pattern"
 */
package se.inera.ifv;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Component("csrfSecurityRequestMatcher")
public class CsrfSecurityRequestMatcher implements RequestMatcher {

  private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

  private Pattern csrfNotRequired =
      Pattern.compile(
          ".*(RecMedCertQuestion|RecMedCertAnswer|FindAllQuestions|FindAllAnswers|DeleteQuestions|DeleteAnswers).*");

  public CsrfSecurityRequestMatcher() {}

  @Override
  public boolean matches(HttpServletRequest httpServletRequest) {
    if (isSafeMethod(httpServletRequest)) return false;
    return requiresCsrf(httpServletRequest);
  }

  private boolean requiresCsrf(HttpServletRequest httpServletRequest) {
    return !csrfNotRequired.matcher(httpServletRequest.getRequestURI()).matches();
  }

  private boolean isSafeMethod(HttpServletRequest httpServletRequest) {
    String tmp = httpServletRequest.getMethod();

    if (allowedMethods.matcher(tmp).matches()) {
      return true;
    }
    return false;
  }
}
