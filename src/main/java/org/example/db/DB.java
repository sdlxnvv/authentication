package org.example.db;

import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface DB {
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);
    List<User> users = new ArrayList<>();
}
