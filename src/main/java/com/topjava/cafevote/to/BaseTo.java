package com.topjava.cafevote.to;

import com.topjava.cafevote.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseTo implements HasId {

    protected Integer id;
}
