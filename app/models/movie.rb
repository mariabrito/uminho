class Movie < ActiveRecord::Base
  has_many :reviews

  def rating
    self.reviews.average(:score) || 0
  end

  def ratings
    self.reviews.count
  end

  def self.find(id)
    Movie::Loader.find(id.to_s)
  end

  def self.trending
    Movie::Loader.all
  end

  def cast
    Movie::Loader.find_cast(id.to_s)
  end
end
