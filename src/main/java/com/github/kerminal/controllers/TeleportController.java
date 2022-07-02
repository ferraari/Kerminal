package com.github.kerminal.controllers;

import com.github.kerminal.Kerminal;
import com.github.kerminal.models.Kit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class TeleportController {

    private Kerminal plugin;

    private HashMap<String, String> requests = new HashMap<>();

    @SneakyThrows
    public void insertRequest(String name, String name2){
        requests.put(name, name2);
    }
    @SneakyThrows
    public void removeRequest(String name, String name2){
        requests.remove(name, name2);
    }
    @SneakyThrows
    public boolean isRequest(String name, String name2){
        return requests.containsKey(name) && requests.containsKey(name2);
    }

}
