package pe.gob.oefa.apps.sinada.presentacion.rest.api.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.util.EmailAttachmentSender;

 
@Service
public class MailJobListener implements JobListener{
	

	
	@Autowired
	InitializingService initializingService;
	
    public String getName() {
        return "MailJobListener";   
    }	
    

 
    public void jobToBeExecuted(JobExecutionContext context) {
    	
    	System.out.println("Inicio de envio de correos "+new Date());
//    	initInitializingService();
//    	EnviCorreoDocumentacionPresentacionFaltante();
    }
 
    public void jobExecutionVetoed(JobExecutionContext context) {
    	System.out.println("Envio de correos cancelado "+new Date());
    }
 
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    	System.out.println("Envio de correos concluido "+new Date()+"\r\n");
    }
     
    
 
}