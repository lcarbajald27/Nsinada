package pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.oefa.apps.sinada.servicio.inf.jobs.JobsService;



@Service
public class InitializingService {

	
	@Autowired
	private JobsService 	jobsService;

	private Scheduler scheduler;
	
	public void initializeAndStartScheduler() throws SchedulerException {
		if(this.scheduler==null){
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();	
		}
		this.scheduler.getContext().put("jobsService", jobsService);
		
		this.scheduler.start();
	}





	public JobsService getJobsService() {
		return jobsService;
	}





	public void setJobsService(JobsService jobsService) {
		this.jobsService = jobsService;
	}





	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
}
