-- Index for default pagination sort (ORDER BY created_at DESC)
CREATE INDEX idx_car_listings_created_at ON car_listings (created_at DESC);

-- Unique constraint: a model slug must be unique within its make
CREATE UNIQUE INDEX uk_car_models_slug_make ON car_models (slug, make_id);
