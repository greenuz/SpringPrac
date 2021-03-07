package com.greenux.blog.repository;


import com.greenux.blog.model.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<Board,Integer>{


}
