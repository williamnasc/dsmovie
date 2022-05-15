package com.devsuperor.dsmovie.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperor.dsmovie.dto.MovieDTO;
import com.devsuperor.dsmovie.entities.Movie;
import com.devsuperor.dsmovie.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAll(Pageable pageable){
		//busca no banco os filmes
		Page<Movie> result = repository.findAll(pageable);
		//converte de Movie para MovieDTO
		Page<MovieDTO> page = result.map(x -> new MovieDTO(x));
		return page;
	}
	
	@Transactional(readOnly = true)
	public MovieDTO findById(Long id){
		//busca no banco o filme
		Movie result = repository.findById(id).get();
		//converte de Movie para MovieDTO
		MovieDTO dto = new MovieDTO(result);
		return dto;
	}
}
