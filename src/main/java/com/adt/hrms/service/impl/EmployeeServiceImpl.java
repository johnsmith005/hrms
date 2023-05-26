package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.repository.EmployeeStatusRepo;
import com.adt.hrms.service.EmployeeService;

	
@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeStatusRepo employeeStatusRepo;
	
	@Override
	public List<Employee> getAllEmps() {
		List<Employee> list=employeeRepo.findAll();
		return list;
	}


	@Override
	public String saveEmp(Employee emp) {
		Optional<Employee> opt=employeeRepo.findById(emp.getEmployeeId());
		if(opt.isPresent())
			return "Employee with Id "+emp.getEmployeeId()+" is already avalable Pls Insert new ID....";
		return employeeRepo.save(emp).getEmployeeId()+" Employee is Saved";
	}

	@Override
	public String deleteEmpById(Integer empId) {
		Optional<Employee> opt=employeeRepo.findById(empId);
		if(opt.isPresent()) {
			employeeRepo.deleteById(empId);
			return empId+" has been Deleted";
		}
		else
			return "Invalid Employe Id :: "+empId;
	}
// 
//	@Override
//	public String updateEmp(Employee emp) {		
//		return employeeRepo.save(emp).getEmployeeId()+ " Details Updated Successfully";
//	}

	@Override
	public EmployeeStatus getEmployeeById(Integer empId) {
		
		Optional<EmployeeStatus> opt=employeeStatusRepo.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;	
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Optional<Employee> emp = employeeRepo.findById(empId);
		return emp.get();
	}

	@Override
	public List<Employee> SearchEmployee(String query) {
		List<Employee> emplist = employeeRepo.SearchEmployee(query);
		return emplist;
	}

	@Override
	public List<Employee> SearchByEmailId(String query) {
		List<Employee> emailemp = employeeRepo.SearchByEmailId(query);
		return emailemp;
	}

	@Override
	public Employee getEmp(Integer empId) {
		Optional<Employee> opt = employeeRepo.findById(empId);
		if(opt.isPresent())
			return opt.get();
		else
			return null; 
	}


	@Override
	public String updateEmp(Integer empId, Employee emp) {
		//return employeeRepo.save(emp).getEmployeeId()+ " Details Updated Successfully";
				Optional<Employee> opt = employeeRepo.findById(empId);
				if(!opt.isPresent())
					return "Employee not found with id: "+empId;
				else
					opt.get().setAccountNumber(emp.getAccountNumber());
					opt.get().setBankName(emp.getBankName());
					opt.get().setDesignation(emp.getDesignation());
					opt.get().setDob(emp.getDob());
					opt.get().setFirstName(emp.getFirstName());
					opt.get().setGender(emp.getGender());
					opt.get().setIfscCode(emp.getIfscCode());
					opt.get().setJoinDate(emp.getJoinDate());
					opt.get().setLastName(emp.getLastName());
					opt.get().setMaritalStatus(emp.getMaritalStatus());
					opt.get().setMobileNo(emp.getMobileNo());
					opt.get().setSalary(emp.getSalary());
					opt.get().setIsActive(emp.getIsActive());
					
//				opt.get().setIsEmailVerified(emp.getIsEmailVerified());
					//opt.get().setEmail(emp.getEmail());
					
					return employeeRepo.save(opt.get()).getEmployeeId()+ " Employee Updated Successfully";
	}
}
