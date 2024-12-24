package nl.belastingdienst.hackmd5.md5;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/md5")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MD5Resource {

    @Inject
    private MD5EncryptionStrategy md5EncryptionStrategy;

    @Inject
    private MD5Repo md5Repo;

    @POST
    @Path("/encrypt")
    public Response encryptPhrase(String phrase) {
        if (phrase == null || phrase.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Phrase cannot be empty")
                    .build();
        }

        String hash = md5EncryptionStrategy.encrypt(phrase);
        return Response.ok(new MD5(hash, phrase)).build();
    }

    @GET
    @Path("/find/{hash}")
    public Response findByHash(@PathParam("hash") String hash) {
        return md5Repo.findHash(hash)
                .map(md5 -> Response.ok(md5).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Hash not found")
                        .build());
    }
}