package com.example.innosynergy.dao;

import com.example.innosynergy.model.Benevolat;
import com.example.innosynergy.model.Event;

import java.util.List;

public interface BenevolatDao {
    void insertBenevolat(Benevolat benevolat);
    List<Benevolat> listBenevolat();
    Benevolat findBenevolatById(int idBenevolat);
    List<Benevolat> searchBenevolats(String keyword);
    void updateBenevolat(Benevolat benevolat);
    void deleteBenevolat(int idBenevolat);
}
