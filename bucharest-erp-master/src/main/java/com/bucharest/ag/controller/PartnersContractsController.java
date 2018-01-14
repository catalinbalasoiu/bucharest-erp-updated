package com.bucharest.ag.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.forms.PartnerForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Function;
import com.bucharest.ag.model.Partner;
import com.bucharest.ag.model.Reminder;
import com.bucharest.ag.repository.PartnerRepository;
import com.bucharest.ag.service.DocumentJpaService;
import com.bucharest.ag.service.DocumentService;
import com.bucharest.ag.service.FileManagerService;
import com.bucharest.ag.service.FunctionService;
import com.bucharest.ag.service.PartnerService;
import com.bucharest.ag.service.PartnerServiceImpl;
import com.bucharest.ag.service.ReminderService;

@Controller
@RequestMapping(value = "/")
public class PartnersContractsController {

	@Autowired
	FileManagerService uploadFileService;
	@Autowired
	DocumentService documentService;
	@Autowired
	ReminderService reminderService;
	@Autowired 
	FunctionService functionService;

	@Autowired
	private PartnerService partnerJpaService;
	
	@Autowired
	private DocumentJpaService documentJpaService;

	private String pageFunction = "Administrative";
	
	//Instantiation of logger class
	//private final static Logger logger = LoggerFactory.getLogger(FileManagerController.class);
	
	@RequestMapping(method = RequestMethod.GET, value = "view-partner-info")
	public String viewPartnerInfo(@RequestParam("type") String partnerType,
			@RequestParam("partnerId") int partnerId, ModelMap model) {
		Partner partner = partnerJpaService.findPartnerById(partnerId);
		model.addAttribute("partner", partner);
		model.addAttribute("partnerType", partnerType);
		model.addAttribute("partnerId", partnerId);
		return "view-partner-info";
	}

	@RequestMapping(method = RequestMethod.GET, value = "get-docs-call")
	@ResponseBody
	public String ajaxListDocs(@RequestParam("partnerId") int partnerId,
			ModelMap model) {
		ObjectMapper mapper = new ObjectMapper();
		List<DocumentForm> docsList = documentJpaService.listPartnerDocs(partnerId);

		String docJson = "";
		try {
			docJson = mapper.writeValueAsString(docsList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"data\": " + docJson + "}";
	}

	@RequestMapping(method = RequestMethod.GET, value = "add-partner")
	public String addPartner(@RequestParam("type") String type, Model model) {
		Partner partner = new Partner();
		model.addAttribute("addPartnerForm", partner);
		model.addAttribute("type", type);
		return "add-partner";
	}

	@RequestMapping(method = RequestMethod.POST, value = "partners-list")
	public String addedPartner(@RequestParam("type") String type,
			@ModelAttribute("addPartnerForm") @Valid Partner partner,
			BindingResult result, ModelMap model, HttpSession session) {
		partner.setPartnerType(type.substring(0, 1));
		partner.setCreatedBy("test");
		partner.setCreatedDate(new Date());
		partner.setUpdatedBy("test");
		partner.setUpdatedDate(new Date());
		partnerJpaService.addPartner(partner);
		String redirectLink = String.format("redirect:view-partners?type=%s",
				type);
		return redirectLink;
	}

	@RequestMapping(method = RequestMethod.GET, value = "view-partners")
	public String viewPartners(
			@ModelAttribute("editPartnerForm") @Valid Partner partner,
			@RequestParam("type") String type, ModelMap model) {
		model.addAttribute("type", type);
		return "partners-list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "get-partners-call")
	@ResponseBody
	public String ajaxListPartners(@RequestParam("type") String type,
			ModelMap model) {
		ObjectMapper mapper = new ObjectMapper();
		String formatedType = type.substring(0, 1);
		List<Partner> partnersList = partnerJpaService.findAllByPartnerType(formatedType);
				//partnerService.
				//listPartners(type);
				//partnerJpaService.findAllByPartnerType(type);
		String partnerJson = "";
		try {
			partnerJson = mapper.writeValueAsString(partnersList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"data\": " + partnerJson + "}";
	}

	@RequestMapping(method = RequestMethod.POST, value = "delete-partner")
	@ResponseBody
	public String deletePartner(@RequestParam("partnerId") int partnerId,
			ModelMap model) {
		partnerJpaService.deletePartnerById(partnerId);
		//partnerService.deletePartnerById(partnerId);
		return "Done";
	}

	@RequestMapping(method = RequestMethod.POST, value = "update-partner")
	@ResponseBody
	public Map<String, Object> updatePartner(Model model,
			@RequestBody PartnerForm partnerForm) {
		Map<String, Object> result = new HashMap<String, Object>();
		Partner partner = partnerJpaService.findPartnerById(partnerForm
				.getPartnerId());
		Date date = new Date();
		partner.setUpdatedDate(date);
		partner.setUpdatedBy("DOREL Modificatoru");
		partner.setName(partnerForm.getName());
		partner.setVatNumber(partnerForm.getVatNumber());
		partner.setContactPerson(partnerForm.getContactPerson());
		partner.setjCode(partnerForm.getjCode());
		partner.setPhoneNumber(partnerForm.getPhoneNumber());
		partner.setTags(partnerForm.getTags());
		partner.setComment(partnerForm.getComment());
		partner.setAddress(partnerForm.getAddress());
		try {
			partnerJpaService.updatePartner(partner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "add-document")
	@ResponseBody
	public int addDocument(ModelMap model,
			@RequestBody DocumentForm documentForm) throws Exception {
		Document document = new Document();

		Date date = new Date();
		document.setPartnerId(documentForm.getPartnerId());
		document.setDocName(documentForm.getDocName());
		document.setDocNumber(documentForm.getDocNumber());
		document.setFileName(documentForm.getFileName());
		document.setExpDate(documentForm.getExpDate());
		document.setComment(documentForm.getComment());
		if (documentForm.getMaster().equals("")){
			document.setMaster(documentForm.getDocNumber());
		} else {
			document.setMaster(documentForm.getMaster());
		}
		document.setTags(documentForm.getTags());
		document.setCreatedBy("Admin");
		document.setCreatedDate(date);
		document.setUpdatedBy("Admin");
		document.setUpdatedDate(date);
		
		//Add the reminder - Verify whether the reminder date has been set
		if(!documentForm.getReminderDate().equals("")){
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date formatedReminderDate = dateFormat.parse(documentForm.getReminderDate());
			
			Reminder reminder = new Reminder();
			reminder.setReminderDate(formatedReminderDate);
			reminder.setReminderStatus(1);
			reminder.setReminderType(pageFunction);
			reminder.setCreatedBy("Admin");
			reminder.setCreatedDate(date);
			reminder.setUpdatedBy("Admin");
			reminder.setUpdatedDate(date);
			
			//Set the functionId for the reminder
			Function functionId = functionService.getFunctionByType(pageFunction);
			reminder.setFunctionId(functionId);
				
			reminderService.addOrEditReminder(reminder);
			document.setReminder(reminder);
		}
		
		try {
			documentService.addOrEditDocument(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document.getDocId();
	}

	@RequestMapping(value = "edit-document", method = RequestMethod.POST)
	@ResponseBody
	public int editDocument(ModelMap model,
			@RequestBody DocumentForm documentForm) throws Exception {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		//Update the document
		Document document = documentService.getDocumentById(documentForm
				.getDocId());
		document.setDocName(documentForm.getDocName());
		document.setDocNumber(documentForm.getDocNumber());
		document.setFileName(documentForm.getFileName());
		document.setExpDate(documentForm.getExpDate());
		document.setComment(documentForm.getComment());
		if (documentForm.getMaster().equals("")){
			document.setMaster(documentForm.getDocNumber());
		} else {
			document.setMaster(documentForm.getMaster());
		}
		document.setTags(documentForm.getTags());
		document.setUpdatedBy("Admin Modificat");
		document.setUpdatedDate(date);
		
		//Update/Edit the reminder date if the reminder date was set on the document
		if(!documentForm.getReminderDate().equals("")){
			if(document.getReminder() == null) {
				Reminder reminder = new Reminder(); 
				Date formatedReminderDate = dateFormat.parse(documentForm.getReminderDate());
				reminder.setReminderDate(formatedReminderDate);
				reminder.setReminderStatus(1);
				reminder.setReminderType(pageFunction);
				reminder.setCreatedBy("Admin");
				reminder.setCreatedDate(date);
				reminder.setUpdatedBy("Admin");
				reminder.setUpdatedDate(date);

				reminderService.addOrEditReminder(reminder);
				document.setReminder(reminder);
			} else {
				Reminder reminder = documentService.getReminderById(documentForm.getReminderId());
				Date formatedReminderDate = dateFormat.parse(documentForm.getReminderDate());
				reminder.setReminderDate(formatedReminderDate);
				reminder.setUpdatedDate(date);
				reminder.setUpdatedBy("Admin modified");
				try {
					reminderService.addOrEditReminder(reminder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			try {
				documentService.addOrEditDocument(document);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				documentService.addOrEditDocument(document);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	@RequestMapping(method = RequestMethod.POST, value = "delete-document")
	@ResponseBody
	public String deleteDocument(@RequestParam("docId") int docId,@RequestParam("reminderId") int reminderId,
			ModelMap model) {
		documentJpaService.deleteDocumentById(docId);
		return "Done";
	}
	
	@RequestMapping(method= RequestMethod.POST, value="verify-master")
	@ResponseBody
	public int verifyMaster(@RequestParam("master") String master, ModelMap model) throws Exception {
		int resultsNumber = documentService.checkIfMasterExists(master);
		return resultsNumber;
	}
	

	@RequestMapping(method= RequestMethod.POST, value="verify-jCode")
	@ResponseBody
	public int verifyJCode(@RequestParam("jCode") String jCode, ModelMap model) throws Exception {
		int resultsNumber = documentService.checkIfJCodeExists(jCode);
		return resultsNumber;
	}
	
	@RequestMapping(method= RequestMethod.POST, value="verify-vatNumber")
	@ResponseBody
	public int verifyVatNumber(@RequestParam("vatNumber") String vatNumber, ModelMap model) throws Exception {
		int resultsNumber = documentService.checkIfVatNumberExists(vatNumber);
		return resultsNumber;
	}
}