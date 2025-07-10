package lk.jiat.bank.web.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.jiat.bank.core.dto.ScheduleTaskDTO;
import lk.jiat.bank.core.model.ScheduledTask;
import lk.jiat.bank.core.service.ScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/scheduled-tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduledTaskResource {

    @EJB
    private ScheduleService scheduleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScheduledTasks(@QueryParam("userId") Long userId) {
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("userId is null").build();
        }

        List<ScheduledTask> scheduledTasks = scheduleService.getAllScheduledTasks(userId);

        List<ScheduleTaskDTO> dtos = scheduledTasks.stream().map(task -> {
            ScheduleTaskDTO dto = new ScheduleTaskDTO();
            dto.setId(task.getId());
            dto.setFromAccount(task.getFromAccount().getAccountNumber());
            dto.setToAccount(task.getToAccount().getAccountNumber());
            dto.setAmount(task.getAmount());
            dto.setStatus(task.getStatus().toString());
            dto.setNextExecutionTime(task.getNextExecutionTime().toString());
            return dto;
        }).collect(Collectors.toList());

        return Response.ok(dtos).build();
    }


    @DELETE
    @Path("/{taskId}")
    public Response cancelTask(@PathParam("taskId") Long taskId) {
        scheduleService.cancelScheduledTask(taskId);
        return Response.noContent().build();
    }

}
