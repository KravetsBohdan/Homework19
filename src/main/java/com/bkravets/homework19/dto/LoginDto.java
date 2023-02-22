package com.bkravets.homework19.dto;


import com.bkravets.homework19.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

public record LoginDto(String email, String password) {
}
