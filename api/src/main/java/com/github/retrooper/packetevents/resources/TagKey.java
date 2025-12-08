package com.github.retrooper.packetevents.resources;

import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecException;
import com.github.retrooper.packetevents.protocol.util.NbtCodecs;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class TagKey {

    public static final NbtCodec<TagKey> CODEC = NbtCodecs.STRING.apply(
            name -> {
                TagKey tagKey = TagKey.tryParse(name);
                if (tagKey == null) {
                    throw new NbtCodecException("Not a tag: " + name);
                }
                return tagKey;
            },
            TagKey::toString
    );

    private final ResourceLocation id;

    public TagKey(ResourceLocation id) {
        this.id = id;
    }

    public static TagKey parse(String name) {
        TagKey tagKey = tryParse(name);
        if (tagKey == null) {
            throw new IllegalArgumentException("Not a tag: " + name);
        }
        return tagKey;
    }

    public static @Nullable TagKey tryParse(String name) {
        if (name.isEmpty() || name.charAt(0) != '#') {
            return null;
        }
        return new TagKey(new ResourceLocation(name.substring(1)));
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return '#' + this.id.toString();
    }
}
