package com.vonnue.grab_resale.persistence.entity;

import com.vonnue.grab_resale.common.utils.SlugUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_makes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMake extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    public CarMake(String name) {
        this.name = name;
        this.slug = SlugUtils.toSlug(name);
    }

    @PrePersist
    private void ensureSlug() {
        if (this.slug == null || this.slug.isBlank()) {
            this.slug = SlugUtils.toSlug(this.name);
        }
    }
}
