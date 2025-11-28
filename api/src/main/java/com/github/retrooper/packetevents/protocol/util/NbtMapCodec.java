package com.github.retrooper.packetevents.protocol.util;

import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface NbtMapCodec<T> extends NbtMapEncoder<T>, NbtMapDecoder<T> {

    default NbtCodec<T> codec() {
        return new NbtCodec<T>() {
            @Override
            public T decode(NBT nbt, PacketWrapper<?> wrapper) throws NbtCodecException {
                NBTCompound compound = nbt.castOrThrow(NBTCompound.class);
                return NbtMapCodec.this.decode(compound, wrapper);
            }

            @Override
            public NBT encode(PacketWrapper<?> wrapper, T value) throws NbtCodecException {
                NBTCompound compound = new NBTCompound();
                NbtMapCodec.this.encode(compound, wrapper, value);
                return compound;
            }
        };
    }
}
