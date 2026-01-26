package ma.clinique.api.mappers;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ma.clinique.api.dto.response.ApiError;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable ex) {

    if (ex instanceof WebApplicationException wae) {
      int status = wae.getResponse().getStatus();
      ApiError err = new ApiError("HTTP_" + status, wae.getMessage());
      return Response.status(status)
          .type(MediaType.APPLICATION_JSON)
          .entity(err)
          .build();
    }

    ApiError err = new ApiError("INTERNAL_ERROR", ex.getMessage());
    return Response.status(500)
        .type(MediaType.APPLICATION_JSON)
        .entity(err)
        .build();
  }
}
