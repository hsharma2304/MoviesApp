package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowMapper implements Mapper<MediaCover,TvShow> {

    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public TvShowMapper(@NonNull ImageQualityConfiguration qualityConfiguration){
        this.qualityConfiguration=qualityConfiguration;
    }

    @Override
    public MediaCover map(TvShow tvShow) {
        MediaCover cover=new MediaCover();
        cover.setMediaId(tvShow.getId());
        cover.setAverageRate(tvShow.getVoteAverage().doubleValue());
        cover.setBackdrops(BackdropImage.convert(tvShow.getBackdrops(),qualityConfiguration));
        cover.setMovieTitle(tvShow.getName());
        cover.setPosterPath(tvShow.getPosterPath());
        cover.setReleaseDate(tvShow.getFirstAirDate());
        cover.setGenres(Genre.convert(tvShow.getGenreList()));
        cover.setMustWatch(tvShow.isMustWatch());
        cover.setWatched(tvShow.isWatched());
        cover.setFavorite(tvShow.isFavorite());
        cover.setTvShow(true);
        return null;
    }

    @Override
    public TvShow reverseMap(MediaCover mediaCover) {
        TvShow tvShow=new TvShow();
        tvShow.setName(mediaCover.getMovieTitle());
        tvShow.setVoteAverage(mediaCover.getAverageRate());
        tvShow.setId(mediaCover.getMediaId());
        tvShow.setFirstAirDate(mediaCover.getReleaseDate());
        tvShow.setPosterPath(mediaCover.getPosterPath());
        tvShow.setBackdrops(BackdropImage.convertToBackdrops(mediaCover.getBackdrops(),qualityConfiguration));
        tvShow.setGenreList(Genre.convertToGenres(mediaCover.getGenres()));
        tvShow.setFavorite(mediaCover.isFavorite());
        tvShow.setWatched(mediaCover.isWatched());
        tvShow.setMustWatch(mediaCover.isMustWatch());
        return tvShow;
    }

    @Override
    public List<MediaCover> map(List<TvShow> from) {
        if(from==null) return null;
        List<MediaCover> result=new ArrayList<>(from.size());
        from.forEach(tvShow -> result.add(map(tvShow)));
        return result;
    }

}
