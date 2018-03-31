package enre.entities;

import org.springframework.beans.factory.annotation.Autowired;

public class RepoService {
	//@--Autowired-----
	private SinLuzRepositorio repo;

	public SinLuzRepositorio getRepo() {
		return repo;
	}

	public void setRepo(SinLuzRepositorio repo) {
		this.repo = repo;
	}
}
