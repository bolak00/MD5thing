package nl.belastingdienst.hackmd5.bruteforce;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.belastingdienst.hackmd5.bruteforce.BruteForceUtil;

import java.util.Optional;

@Path("/bruteforce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BruteForceResource {

    @Inject
    private BruteForceUtil bruteForceUtil;

    @GET
    @Path("/find")
    public Response findPhraseForHash(@QueryParam("hash") String hash, @QueryParam("maxLength") int maxLength) {
        Optional<String> phrase = bruteForceUtil.findPhraseForHash(hash, maxLength);
        if (phrase.isPresent()) {
            return Response.ok(phrase.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}