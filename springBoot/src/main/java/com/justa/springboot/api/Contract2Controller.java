package com.justa.springboot.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justa.springboot.db.Contract2Repository;
import com.justa.springboot.model.Contract2;
import com.justa.springboot.model.ContractInfoExt;

@Controller 
@RequestMapping(path = "/api/program/") 
public class Contract2Controller {
	
	@Autowired
	private Contract2Repository cRepo;
	
	@GetMapping(path = "{programId}/contracts")
	public @ResponseBody List<Contract2> getContractsByProgramId(
			@PathVariable("programId") Integer programId
			) {
			
		List<Contract2> result = cRepo.getContractsByProgramId(programId);
		return result;
	}
	@GetMapping(path = "{programId}/fullContracts")
	public @ResponseBody List<ContractInfoExt> getFullContractsByProgramId(
			@PathVariable("programId") Integer programId
			) {
			
		List<ContractInfoExt> result = cRepo.getFullContractsByProgramId(programId);
		return result;
	}
}
