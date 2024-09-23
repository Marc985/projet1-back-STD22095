package org.event.backendpatrimoine.modal;

import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
public class Patrimoiny {
    private int id;
    private String name;
    private LocalDateTime updateDate;
}
