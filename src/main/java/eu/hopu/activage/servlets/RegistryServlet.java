package eu.hopu.activage.servlets;

import eu.hopu.activage.Initializer;
import eu.hopu.activage.services.RegistryService;
import eu.hopu.activage.services.dto.ImageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("registry")
public class RegistryServlet {

    private RegistryService registryService;

    public RegistryServlet() {
        this.registryService = Initializer.service;
    }

    @GET
    @Path("images")
    @Produces({"application/json", "plain/text"})
    public Response listImages(@Context HttpServletRequest request) {
        List<String> images = registryService.getImages();
        return Response.ok().entity(images).build();
    }

    @GET
    @Path("images/{imageId}/tags")
    @Produces({"application/json", "plain/text"})
    public Response listTagsByImageId(@Context HttpServletRequest request, @PathParam("imageId") String imageId) {
        List<String> tags = registryService.getImagTags(imageId);
        return Response.ok().entity(tags).build();
    }

    @GET
    @Path("images/{imageId}/info")
    @Produces({"application/json", "plain/text"})
    public Response listImageInfoByImageId(@Context HttpServletRequest request, @PathParam("imageId") String imageId) {
        ImageInfo imageInfo = registryService.getImageInfo(imageId);
        if (imageInfo != null)
            return Response.ok().entity(imageInfo).build();
        else
            return Response.status(404).build();
    }

    @POST
    @Path("images/{imageId}/info")
    @Produces({"application/json", "plain/text"})
    public Response createImageInfo(@Context HttpServletRequest request, @PathParam("imageId") String imageId,
                                    String imageInfo) {
        ImageInfo info = registryService.addImageInfo(imageId, imageInfo);
        return Response.ok().entity(info).build();
    }

    @PUT
    @Path("images/{imageId}/info")
    @Produces({"application/json", "plain/text"})
    public Response updateImageInfo(@Context HttpServletRequest request, @PathParam("imageId") String imageId,
                                    String imageInfo) {
        ImageInfo info = registryService.updateImageInfo(imageId, imageInfo);
        return Response.ok().entity(info).build();
    }

    @DELETE
    @Path("images/{imageId}/info")
    @Produces({"application/json", "plain/text"})
    public Response deleteImageInfo(@Context HttpServletRequest request, @PathParam("imageId") String imageId) {
        boolean response = registryService.deleteImageInfo(imageId);
        return response ? Response.noContent().build() : Response.status(400).build();
    }

}
