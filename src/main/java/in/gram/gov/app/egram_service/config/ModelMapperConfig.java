//package in.gram.gov.app.egram_service.config;
//
//import in.gram.gov.app.egram_service.domain.entity.*;
//import in.gram.gov.app.egram_service.dto.response.*;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelMapperConfig {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setSkipNullEnabled(true);
//
//        // 👇 Add custom mapping
//        mapper.addMappings(new PropertyMap<User, UserResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setUserId(source.getId());
//            }
//        });
//
//        mapper.addMappings(new PropertyMap<Album, AlbumResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setAlbumId(source.getId());
//            }
//        });
//        mapper.addMappings(new PropertyMap<Comment, CommentResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setCommentId(source.getId());
//            }
//        });
//
//        mapper.addMappings(new PropertyMap<Document, DocumentResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setDocumentId(source.getId());
//            }
//        });
//
//        mapper.addMappings(new PropertyMap<GalleryImage, GalleryImageResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setImageId(source.getId());
//            }
//        });
//
//        mapper.addMappings(new PropertyMap<Scheme, SchemeResponseDTO>() {
//            @Override
//            protected void configure() {
//                map().setSchemeId(source.getId());
//            }
//        });
//
//        return mapper;
//    }
//}
