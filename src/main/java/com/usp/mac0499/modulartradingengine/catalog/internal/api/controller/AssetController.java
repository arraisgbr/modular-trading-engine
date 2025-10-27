package com.usp.mac0499.modulartradingengine.catalog.internal.api.controller;

import com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.request.AssetRequest;
import com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response.AssetResponse;
import com.usp.mac0499.modulartradingengine.catalog.internal.api.mappers.AssetMapper;
import com.usp.mac0499.modulartradingengine.catalog.internal.service.interfaces.IAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final IAssetService assetService;

    private final AssetMapper assetMapper;

    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@RequestBody AssetRequest assetRequest) {
        var asset = assetService.createAsset(assetRequest.code(), assetRequest.value(), assetRequest.quantity());
        var assetResponse = assetMapper.toResponse(asset);
        var location = fromCurrentRequest().path("/{id}").buildAndExpand(assetResponse.id()).toUri();
        return ResponseEntity.created(location).body(assetResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> readAsset(@PathVariable UUID id) {
        var asset = assetService.readAsset(id);
        var assetResponse = assetMapper.toResponse(asset);
        return ResponseEntity.ok(assetResponse);
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> readAssets() {
        var assets = assetService.readAssets();
        var assetsResponse = assets.stream().map(assetMapper::toResponse).toList();
        return ResponseEntity.ok(assetsResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable UUID id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

}
