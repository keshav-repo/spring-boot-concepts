package com.example.mongodb.document;

import org.springframework.data.annotation.Id;

public class Node {
    @Id
    int nodeId;

    Node next;


}
