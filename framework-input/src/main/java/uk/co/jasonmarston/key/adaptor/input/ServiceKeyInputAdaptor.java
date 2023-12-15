package uk.co.jasonmarston.key.adaptor.input;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import uk.co.jasonmarston.key.usecase.KeyUseCase;

@ApplicationScoped
@Path("/service-api")
public class ServiceKeyInputAdaptor {
	private static final Response NOT_FOUND = Response
		.status(Status.NOT_FOUND)
		.build();

	@Inject
	private KeyUseCase keyUseCase;

	@GET
	@Path("/pem")
	public Uni<Response> readKey() {
		return keyUseCase
			.readKey()
			.onItem()
			.transform(pemString -> Response
				.ok(pemString)
				.build()
			)
			.onFailure()
			.recoverWithItem(NOT_FOUND);
	} 
}
