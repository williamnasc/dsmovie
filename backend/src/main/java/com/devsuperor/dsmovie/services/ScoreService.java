package com.devsuperor.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperor.dsmovie.dto.MovieDTO;
import com.devsuperor.dsmovie.dto.ScoreDTO;
import com.devsuperor.dsmovie.entities.Movie;
import com.devsuperor.dsmovie.entities.Score;
import com.devsuperor.dsmovie.entities.User;
import com.devsuperor.dsmovie.repositories.MovieRepository;
import com.devsuperor.dsmovie.repositories.ScoreRepository;
import com.devsuperor.dsmovie.repositories.UserRepository;

@Service
public class ScoreService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ScoreRepository scoreRepository;
	
	@Transactional
	public MovieDTO saveScore(ScoreDTO dto) {
		
		//busca do banco o usuario pelo email submetido
		User user = userRepository.findByEmail(dto.getEmail());
		if(user == null) {
			//criar user com os dados submetidos
			user = new User();
			user.setEmail(dto.getEmail());
			//salva novo user no banco
			user = userRepository.saveAndFlush(user);
		}
		
		//puxa filme do banco
		Movie movie = movieRepository.findById(dto.getMovieId()).get();
		
		//criar score
		Score score = new Score();
		score.SetMovie(movie);
		score.SetUser(user);
		score.setValue(dto.getScore());
		
		//salva score
		score = scoreRepository.saveAndFlush(score);
		
		//recalcular a avaliacao media do filme e salvar no banco
		double sum = 0.0;
		for(Score s : movie.getScores()) {
			 sum = sum + s.getValue();
		}
		
		double avg = sum/movie.getScores().size();
		
		// att e salva nova media
		movie.setScore(avg);
		movie.setCount(movie.getScores().size());
		
		movie = movieRepository.save(movie);
		
		return new MovieDTO(movie);
	}
	
}
