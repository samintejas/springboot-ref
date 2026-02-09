package com.vonnue.grab_resale.persistence.entity;

import com.vonnue.grab_resale.common.utils.SlugUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_models", uniqueConstraints = @UniqueConstraint(columnNames = {"slug", "make_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarModel extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake make;

    public CarModel(String name, CarMake make) {
        this.name = name;
        this.slug = SlugUtils.toSlug(name);
        this.make = make;
    }

    @PrePersist
    private void ensureSlug() {
        if (this.slug == null || this.slug.isBlank()) {
            this.slug = SlugUtils.toSlug(this.name);
        }
    }
}
