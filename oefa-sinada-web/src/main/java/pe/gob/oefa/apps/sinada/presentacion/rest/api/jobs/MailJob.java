package pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs;




import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;
import pe.gob.oefa.apps.sinada.servicio.inf.jobs.JobsService;



public class MailJob implements Job {

	@Autowired
	JobsService jobsService;
	
//	@Override
//	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		// TODO Auto-generated method stub
//		
//	}
	

	
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		try {
			jobsService =(JobsService)jec.getScheduler().getContext().get("jobsService");
			this.procesar();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		

	}

	public void procesar() {
		
		
		if (jobsService!=null) {
			try {
				
				jobsService.EnviCorreoNotificacionDenuncias();
			} catch (ServicioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public JobsService getJobsService() {
		return jobsService;
	}

	public void setJobsService(JobsService jobsService) {
		this.jobsService = jobsService;
	}

	

	
	
//	public JobService getJobService() {
//		return jobService;
//	}
//
//	public void setJobService(JobService jobService) {
//		this.jobService = jobService;
//	}
	
}