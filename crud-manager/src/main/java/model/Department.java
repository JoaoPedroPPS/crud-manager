package model;

public class Department {
	private int id;
	private String nome;
	private String manager;
	private float budget;
	private Company company;
	
	public Department() {
		
	}
	
	public Department(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public int getCompanyId() {
		return company.getId();
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}
	
	

}
