package nuri.nuri_server.global.feign.google_map;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.common.GeoPointDto;
import nuri.nuri_server.global.feign.google_map.exception.AddressNotFoundException;
import nuri.nuri_server.global.feign.google_map.exception.GoogleMapsException;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeoApiService {

    private final GeoApiContext geoApiContext;

    public String getRegionFromAddress(String address) {
        GeocodingResult[] results = getGeoCode(address);

        AddressComponent[] comps = results[0].addressComponents;

        Map<AddressComponentType, String> detailAddressComponents = new EnumMap<>(AddressComponentType.class);
        for (AddressComponent c : comps) {
            for (AddressComponentType t : c.types) {
                System.out.println("t = " + t + " c.longName = " + c.longName);
                detailAddressComponents.putIfAbsent(t, c.longName);
            }
        }

        return getRegionFromAddressComponent(detailAddressComponents);
    }

    public GeoPointDto getGeoPointFromAddress(String address) {
        GeocodingResult[] results = getGeoCode(address);

        Double lat = results[0].geometry.location.lat;
        Double lon = results[0].geometry.location.lng;

        return GeoPointDto.builder()
                .lat(lat)
                .lon(lon)
                .build();
    }

    private GeocodingResult[] getGeoCode(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address)
                    .language("ko")
                    .await();

            if (results.length == 0) throw new AddressNotFoundException(address);

            return results;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GoogleMapsException("구글 주소 변환중 에러가 발생했습니다.");
        }
    }

    private String getRegionFromAddressComponent(Map<AddressComponentType, String> detailAddressComponents) {
        String country = detailAddressComponents.getOrDefault(AddressComponentType.COUNTRY, "");
        String city   = detailAddressComponents.getOrDefault(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1, "");
        String gu = detailAddressComponents.getOrDefault(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2,
                detailAddressComponents.getOrDefault(AddressComponentType.SUBLOCALITY_LEVEL_1, ""));;

        return country + " " + city + " " + gu;
    }
}
